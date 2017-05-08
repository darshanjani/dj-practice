package org.joget;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.exception.ObjectLookupException;
import org.displaytag.util.LookupUtil;
import org.joget.apps.app.model.AppDefinition;
import org.joget.apps.app.service.AppPluginUtil;
import org.joget.apps.app.service.AppUtil;
import org.joget.apps.datalist.model.DataList;
import org.joget.apps.datalist.model.DataListColumn;
import org.joget.apps.datalist.model.DataListColumnFormatDefault;
import org.joget.commons.util.LogUtil;
import org.joget.commons.util.StringUtil;
import org.joget.workflow.util.WorkflowUtil;

public class AmazonS3DatalistFormatter extends DataListColumnFormatDefault {

	private static final String MESSAGE_PATH = "messages/AmazonS3FileLinkDLFormatter";

	public String getName() {
		return "Amazon S3 File Link DL Formatter";
	}

	public String getVersion() {
		return "5.0.0";
	}

	public String getClassName() {
		return getClass().getName();
	}

	public String getLabel() {
		return AppPluginUtil.getMessage("org.joget.AmazonS3DatalistFormatter.pluginLabel", getClassName(),
				MESSAGE_PATH);
	}

	public String getDescription() {
		return AppPluginUtil.getMessage("org.joget.AmazonS3DatalistFormatter.pluginDesc", getClassName(),
				MESSAGE_PATH);
	}

	public String getPropertyOptions() {
		return AppUtil.readPluginResource(getClassName(), "/properties/amazonS3FileLinkDLFormatter.json", null, true,
				MESSAGE_PATH);
	}

	public String format(DataList dataList, DataListColumn column, Object row, Object value) {
		String result = (String) value;
		if ((result != null) && (!result.isEmpty())) {
			try {
				String formDefId = getPropertyString("formDefId");
				AppDefinition appDef = AppUtil.getCurrentAppDefinition();

				result = "";
				String attachment = "";
				if ("true".equals(getPropertyString("attachment"))) {
					attachment = "?attachment=true";
				}

				String primaryKeyValue = (String) LookupUtil.getBeanProperty(row,
						dataList.getBinder().getPrimaryKeyColumnName());

				HttpServletRequest request = WorkflowUtil.getHttpServletRequest();

				for (String v : value.toString().split(";")) {
					if (v.isEmpty())
						continue;
					String fileName = v;
					String encodedFileName = fileName;
					try {
						encodedFileName = URLEncoder.encode(fileName, "UTF8").replaceAll("\\+", "%20");
					} catch (UnsupportedEncodingException ex) {
					}
					String filePath = request.getContextPath() + "/web/client/app/" + appDef.getAppId() + "/"
							+ appDef.getVersion().toString() + "/form/download/" + formDefId + "/" + primaryKeyValue
							+ "/" + encodedFileName + "." + attachment;

					if (!result.isEmpty()) {
						result = result + ", ";
					}

					result = result + "<a href=\"" + filePath + "\" target=\"_blank\">"
							+ StringUtil.stripAllHtmlTag(fileName) + "</a>";
				}
			} catch (Exception e) {
				LogUtil.error(getClassName(), e, "");
			}
		}
		return result;
	}
}
