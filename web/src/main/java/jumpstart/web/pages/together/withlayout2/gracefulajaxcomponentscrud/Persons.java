package jumpstart.web.pages.together.withlayout2.gracefulajaxcomponentscrud;

import jumpstart.web.components.together.gracefulajaxcomponentscrud.PersonEditor.Mode;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

public class Persons {

	// The activation context

	@Property
	private Mode editorMode;

	@Property
	private Long editorPersonId;

	@ActivationRequestParameter
	private Mode arpEditorMode;

	@ActivationRequestParameter
	private Long arpEditorPersonId;
	
	// Screen fields

	@Property
	@Persist
	private boolean highlightZoneUpdates;

	@Property
	// If we use @ActivationRequestParameter instead of @Persist, then our handler for filter form success would have
	// to render more than just the listZone, it would have to render all other links and forms: it would need a zone
	// around the "Create..." link so it could render it; and it would render the editorZone, which would be destructive
	// if the user has been typing into Create or Update. Alternatively, it could use a custom JavaScript callback to
	// update the partialName in all other links and forms - see AjaxResponseRenderer#addCallback(JavaScriptCallback).
	@Persist
	private String partialName;

	@Property
	private Long listPersonId;

	// Generally useful bits and pieces

	@InjectComponent
	private Zone listZone;

	@InjectComponent
	private Zone editorZone;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	// The code

	// onPassivate() is called by Tapestry to get the activation context to put in the URL.

	Object[] onPassivate() {

		if (request.isXHR()) {
			arpEditorMode = editorMode;
			arpEditorPersonId = editorPersonId;
			
			return null;
		}
		else {
			
			if (editorMode == null) {
				return null;
			}
			else if (editorMode == Mode.CREATE) {
				return new Object[] { editorMode };
			}
			else if (editorMode == Mode.REVIEW || editorMode == Mode.UPDATE
					|| editorMode == Mode.CONFIRM_DELETE) {
				return new Object[] { editorMode, editorPersonId };
			}
			else {
				throw new IllegalStateException(editorMode.toString());
			}
		}

	}

	// onActivate() is called by Tapestry to pass in the activation context from the URL.

	void onActivate(EventContext ec) {

		if (request.isXHR()) {
			editorMode = arpEditorMode;
			editorPersonId = arpEditorPersonId;
		}
		else {
			if (ec.getCount() == 0) {
				editorMode = null;
				editorPersonId = null;
			}
			else if (ec.getCount() == 1) {
				editorMode = ec.get(Mode.class, 0);
				editorPersonId = null;
			}
			else {
				editorMode = ec.get(Mode.class, 0);
				editorPersonId = ec.get(Long.class, 1);
			}
		}

		listPersonId = editorPersonId;
	}

	// setupRender() is called by Tapestry right before it starts rendering the page.

	void setupRender() {
		listPersonId = editorPersonId;
	}

	// /////////////////////////////////////////////////////////////////////
	// FILTER
	// /////////////////////////////////////////////////////////////////////

	// Handle event "filter" from component "list"

	void onFilterFromList() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(listZone);
		}
	}

	// /////////////////////////////////////////////////////////////////////
	// CREATE
	// /////////////////////////////////////////////////////////////////////

	// Handle event "toCreate" from this page

	void onToCreate() {
		editorMode = Mode.CREATE;
		editorPersonId = null;
		listPersonId = null;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(listZone).addRender(editorZone);
		}
	}

	// Handle event "cancelCreate" from component "editor"

	void onCancelCreateFromEditor() {
		editorMode = null;
		editorPersonId = null;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(editorZone);
		}
	}

	// Handle event "successfulCreate" from component "editor"

	void onSuccessfulCreateFromEditor(Long personId) {
		editorMode = Mode.REVIEW;
		editorPersonId = personId;
		listPersonId = personId;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(listZone).addRender(editorZone);
		}
	}

	// Handle event "failedCreate" from component "editor"

	void onFailedCreateFromEditor() {
		editorMode = Mode.CREATE;
		editorPersonId = null;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(editorZone);
		}
	}

	// /////////////////////////////////////////////////////////////////////
	// REVIEW
	// /////////////////////////////////////////////////////////////////////

	// Handle event "selected" from component "list"

	void onSelectedFromList(Long personId) {
		editorMode = Mode.REVIEW;
		editorPersonId = personId;
		listPersonId = personId;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(listZone).addRender(editorZone);
		}
	}

	// /////////////////////////////////////////////////////////////////////
	// UPDATE
	// /////////////////////////////////////////////////////////////////////

	// Handle event "toUpdate" from component "editor"

	void onToUpdateFromEditor(Long personId) {
		editorMode = Mode.UPDATE;
		editorPersonId = personId;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(editorZone);
		}
	}

	// Handle event "cancelUpdate" from component "editor"

	void onCancelUpdateFromEditor(Long personId) {
		editorMode = Mode.REVIEW;
		editorPersonId = personId;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(editorZone);
		}
	}

	// Handle event "successfulUpdate" from component "editor"

	void onSuccessfulUpdateFromEditor(Long personId) {
		editorMode = Mode.REVIEW;
		editorPersonId = personId;
		listPersonId = personId;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(listZone).addRender(editorZone);
		}
	}

	// Handle event "failedUpdate" from component "editor"

	void onFailedUpdateFromEditor(Long personId) {
		editorMode = Mode.UPDATE;
		editorPersonId = personId;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(editorZone);
		}
	}

	// /////////////////////////////////////////////////////////////////////
	// DELETE
	// /////////////////////////////////////////////////////////////////////

	// Handle event "successfulDelete" from component "editor"

	void onSuccessfulDeleteFromEditor(Long personId) {
		editorMode = null;
		editorPersonId = null;
		listPersonId = null;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(listZone).addRender(editorZone);
		}
	}

	// Handle event "failedDelete" from component "editor"

	void onFailedDeleteFromEditor(Long personId) {
		editorMode = Mode.REVIEW;
		editorPersonId = personId;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(editorZone);
		}
	}

	// /////////////////////////////////////////////////////////////////////
	// CONFIRM DELETE - used only when JavaScript is disabled.
	// /////////////////////////////////////////////////////////////////////

	// Handle event "toConfirmDelete" from component "editor"

	void onToConfirmDeleteFromEditor(Long personId) {
		editorMode = Mode.CONFIRM_DELETE;
		editorPersonId = personId;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(editorZone);
		}
	}

	// Handle event "cancelConfirmDelete" from component "editor"

	Object onCancelConfirmDeleteFromEditor(Long personId) {
		editorMode = Mode.REVIEW;
		editorPersonId = personId;
		listPersonId = personId;
		return null;
	}

	// Handle event "successfulConfirmDelete" from component "editor"

	void onSuccessfulConfirmDeleteFromEditor(Long personId) {
		editorMode = null;
		editorPersonId = personId;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(editorZone);
		}
	}

	// Handle event "failedConfirmDelete" from component "editor"

	void onFailedConfirmDeleteFromEditor(Long personId) {
		editorMode = Mode.CONFIRM_DELETE;
		editorPersonId = personId;

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(editorZone);
		}
	}

	// /////////////////////////////////////////////////////////////////////
	// GETTERS
	// /////////////////////////////////////////////////////////////////////

	public String getZoneUpdateFunction() {
		return highlightZoneUpdates ? "highlight" : "show";
	}

}
