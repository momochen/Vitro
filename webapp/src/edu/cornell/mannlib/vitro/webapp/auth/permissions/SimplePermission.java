/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vitro.webapp.auth.permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.cornell.mannlib.vitro.webapp.auth.requestedAction.Actions;
import edu.cornell.mannlib.vitro.webapp.auth.requestedAction.SimpleRequestedAction;
import edu.cornell.mannlib.vitro.webapp.auth.requestedAction.ifaces.RequestedAction;

/**
 * A class of simple permissions. Each instance holds a RequestedAction, and
 * will only authorize that RequestedAction (or one with the same URI).
 */
public class SimplePermission extends Permission {
	private static final Log log = LogFactory.getLog(SimplePermission.class);

	private static final String NAMESPACE = "java:"
			+ SimplePermission.class.getName() + "#";

	private static final Map<String, SimplePermission> allInstances = new HashMap<String, SimplePermission>();

	public static final SimplePermission ACCESS_SPECIAL_DATA_MODELS = new SimplePermission(
			"AccessSpecialDataModels");
	public static final SimplePermission DO_BACK_END_EDITING = new SimplePermission(
			"DoBackEndEditing");
	public static final SimplePermission DO_FRONT_END_EDITING = new SimplePermission(
			"DoFrontEndEditing");
	public static final SimplePermission EDIT_ONTOLOGY = new SimplePermission(
			"EditOntology");
	public static final SimplePermission EDIT_OWN_ACCOUNT = new SimplePermission(
			"EditOwnAccount");
	public static final SimplePermission EDIT_SITE_INFORMATION = new SimplePermission(
			"EditSiteInformation");
	public static final SimplePermission LOGIN_DURING_MAINTENANCE = new SimplePermission(
			"LoginDuringMaintenance");
	public static final SimplePermission MANAGE_MENUS = new SimplePermission(
			"ManageMenus");
	public static final SimplePermission MANAGE_OWN_PROXIES = new SimplePermission(
			"ManageOwnProxies");
	public static final SimplePermission MANAGE_PORTALS = new SimplePermission(
			"ManagePortals");
	public static final SimplePermission MANAGE_PROXIES = new SimplePermission(
			"ManageProxies");
	public static final SimplePermission MANAGE_SEARCH_INDEX = new SimplePermission(
			"ManageSearchIndex");
	public static final SimplePermission MANAGE_TABS = new SimplePermission(
			"ManageTabs");
	public static final SimplePermission MANAGE_USER_ACCOUNTS = new SimplePermission(
			"ManageUserAccounts");
	public static final SimplePermission QUERY_FULL_MODEL = new SimplePermission(
			"QueryFullModel");
	public static final SimplePermission QUERY_USER_ACCOUNTS_MODEL = new SimplePermission(
			"QueryUserAccountsModel");
	public static final SimplePermission REBUILD_VCLASS_GROUP_CACHE = new SimplePermission(
			"RebuildVClassGroupCache");
	public static final SimplePermission REFRESH_VISUALIZATION_CACHE = new SimplePermission(
			"RefreshVisualizationCache");
	public static final SimplePermission SEE_INDVIDUAL_EDITING_PANEL = new SimplePermission(
			"SeeIndividualEditingPanel");
	public static final SimplePermission SEE_REVISION_INFO = new SimplePermission(
			"SeeRevisionInfo");
	public static final SimplePermission SEE_SITE_ADMIN_PAGE = new SimplePermission(
			"SeeSiteAdminPage");
	public static final SimplePermission SEE_STARTUP_STATUS = new SimplePermission(
			"SeeStartupStatus");
	public static final SimplePermission SEE_VERBOSE_PROPERTY_INFORMATION = new SimplePermission(
			"SeeVerbosePropertyInformation");
	public static final SimplePermission USE_ADVANCED_DATA_TOOLS_PAGES = new SimplePermission(
			"UseAdvancedDataToolsPages");
	public static final SimplePermission USE_BASIC_AJAX_CONTROLLERS = new SimplePermission(
			"UseBasicAjaxControllers");
	public static final SimplePermission USE_MISCELLANEOUS_ADMIN_PAGES = new SimplePermission(
			"UseMiscellaneousAdminPages");
	public static final SimplePermission USE_MISCELLANEOUS_CURATOR_PAGES = new SimplePermission(
			"UseMiscellaneousCuratorPages");
	public static final SimplePermission USE_MISCELLANEOUS_EDITOR_PAGES = new SimplePermission(
			"UseMiscellaneousEditorPages");
	public static final SimplePermission USE_MISCELLANEOUS_PAGES = new SimplePermission(
			"UseMiscellaneousPages");
	public static final SimplePermission USE_SPARQL_QUERY_PAGE = new SimplePermission(
			"UseSparqlQueryPage");

	public static List<SimplePermission> getAllInstances() {
		return new ArrayList<SimplePermission>(allInstances.values());
	}

	private final String localName;
	public final RequestedAction ACTION;
	public final Actions ACTIONS;

	public SimplePermission(String localName) {
		super(NAMESPACE + localName);
		
		if (localName == null) {
			throw new NullPointerException("name may not be null.");
		}

		this.localName = localName;
		this.ACTION = new SimpleRequestedAction(localName);
		this.ACTIONS = new Actions(this.ACTION);

		if (allInstances.containsKey(this.uri)) {
			throw new IllegalStateException("A SimplePermission named '"
					+ this.uri + "' already exists.");
		}
		allInstances.put(uri, this);
	}

	public String getLocalName() {
		return this.localName;
	}

	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public boolean isAuthorized(RequestedAction whatToAuth) {
		if (whatToAuth != null) {
			if (ACTION.getURI().equals(whatToAuth.getURI())) {
				log.debug(this + " authorizes " + whatToAuth);
				return true;
			}
		}
		log.debug(this + " does not authorize " + whatToAuth);
		return false;
	}

	@Override
	public String toString() {
		return "SimplePermission['" + localName + "']";
	}

}
