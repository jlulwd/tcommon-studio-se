// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.tis;

import org.talend.core.IService;
import org.talend.core.model.properties.Project;
import org.talend.core.model.properties.Property;

/**
 * DOC bZhou class global comment. Detailled comment
 */
public interface ITDQImportExportService extends IService {

    /**
     * DOC bZhou Comment method "importElement".
     * 
     * @param project
     * @param property
     * @param version
     * @return
     */
    public boolean importElement(Project project, Property property, String version);
}
