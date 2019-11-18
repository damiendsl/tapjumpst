package jumpstart.web.model.examples.ajax;

import java.util.ArrayList;
import java.util.List;

import jumpstart.business.domain.person.Person;
import jumpstart.business.domain.person.Regions;
import jumpstart.business.domain.person.iface.IPersonFinderServiceRemote;
import jumpstart.util.query.SortCriterion;
import jumpstart.util.query.SortDirection;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;

public class PersonFilteredDataSource implements GridDataSource {
	private IPersonFinderServiceRemote personFinderService;
	private String firstInitial;
	private String lastInitial;
	private Regions region;

	private int startIndex;
	private List<Person> preparedResults;

	public PersonFilteredDataSource(IPersonFinderServiceRemote personFinderService, String firstInitial,
			String lastInitial, Regions region) {
		this.personFinderService = personFinderService;
		this.firstInitial = firstInitial;
		this.lastInitial = lastInitial;
		this.region = region;
	}

	@Override
	public int getAvailableRows() {
		return (int) personFinderService.countPersons(firstInitial, lastInitial, region);
	}

	@Override
	public void prepare(final int startIndex, final int endIndex, final List<SortConstraint> sortConstraints) {

		// Get a filtered page of persons - ask business service to find them (from the database)
		List<SortCriterion> sortCriteria = toSortCriteria(sortConstraints);
		preparedResults = personFinderService.findPersons(firstInitial, lastInitial, region, startIndex, endIndex
				- startIndex + 1, sortCriteria);

		this.startIndex = startIndex;
	}

	@Override
	public Object getRowValue(final int index) {
		return preparedResults.get(index - startIndex);
	}

	@Override
	public Class<Person> getRowType() {
		return Person.class;
	}

	/**
	 * Converts a list of Tapestry's SortConstraint to a list of our business tier's SortCriterion. The business tier
	 * does not use SortConstraint because that would create a dependency on Tapestry.
	 */
	private List<SortCriterion> toSortCriteria(List<SortConstraint> sortConstraints) {
		List<SortCriterion> sortCriteria = new ArrayList<SortCriterion>();

		for (SortConstraint sortConstraint : sortConstraints) {

			String propertyName = sortConstraint.getPropertyModel().getPropertyName();
			SortDirection sortDirection = SortDirection.UNSORTED;

			switch (sortConstraint.getColumnSort()) {
			case ASCENDING:
				sortDirection = SortDirection.ASCENDING;
				break;
			case DESCENDING:
				sortDirection = SortDirection.DESCENDING;
				break;
			default:
			}

			SortCriterion sortCriterion = new SortCriterion(propertyName, sortDirection);
			sortCriteria.add(sortCriterion);
		}

		return sortCriteria;
	}

}
