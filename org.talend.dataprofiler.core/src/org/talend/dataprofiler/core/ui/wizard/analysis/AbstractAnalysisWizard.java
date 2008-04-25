// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.wizard.analysis;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorPart;
import org.talend.cwm.constants.DevelopmentStatus;
import org.talend.cwm.helper.DescriptionHelper;
import org.talend.cwm.helper.TaggedValueHelper;
import org.talend.dataprofiler.core.CorePlugin;
import org.talend.dataprofiler.core.exception.DataprofilerCoreException;
import org.talend.dataprofiler.core.exception.ExceptionHandler;
import org.talend.dataprofiler.core.helper.AnaResourceFileHelper;
import org.talend.dataprofiler.core.ui.editor.AnalysisEditor;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.analysis.AnalysisType;
import org.talend.dq.analysis.AnalysisBuilder;
import org.talend.dq.analysis.AnalysisWriter;
import org.talend.dq.analysis.parameters.ConnectionAnalysisParameter;
import org.talend.dq.analysis.parameters.ConnectionParameter;
import org.talend.utils.sugars.TypedReturnCode;

/**
 * AbstractAnalysisWizard can creat a empty analysis file.
 */
public abstract class AbstractAnalysisWizard extends Wizard {

    static Logger log = Logger.getLogger(AbstractAnalysisWizard.class);

    protected String analysisName;

    protected AnalysisType analysisType;

    /**
     * The folder where to save the analysis.
     */
    protected String pathName;

    public AbstractAnalysisWizard() {
        super();
    }

    @Override
    public boolean performFinish() {
        // CorePlugin.getDefault().openEditor(folderProvider.getFolder());
        IEditorPart openEditor = null;

        this.fillAnalysisEditorParam();
        if (!checkAnalysisEditorParam()) {
            return false;
        }

        try {
            File file = createEmptyAnalysisFile();
            if (file == null) {
                return false;
            }
            openEditor = CorePlugin.getDefault().openEditor(file);
        } catch (Exception e) {
            ExceptionHandler.process(e, Level.ERROR);
        }
        if (openEditor != null) {
            ((AnalysisEditor) openEditor).setDirty(true);
        }
        return true;
    }

    protected abstract void fillAnalysisEditorParam();

    private boolean checkAnalysisEditorParam() {
        if (analysisType == null) {
            log.error("The field 'analysisType' haven't assigned a value.");
            return false;
        } else if (analysisName == null) {
            log.error("The field 'analysisName' haven't assigned a value.");
            return false;
        } else if (pathName == null) {
            log.error("The field 'fileURI' haven't assigned a value.");
            return false;
        }
        return true;
    }

    private File createEmptyAnalysisFile() throws DataprofilerCoreException {
        AnalysisBuilder analysisBuilder = new AnalysisBuilder();
        boolean analysisInitialized = analysisBuilder.initializeAnalysis(analysisName, analysisType);
        if (!analysisInitialized) {
            throw new DataprofilerCoreException(analysisName + " failed to initialize!");
        }
        Analysis analysis = analysisBuilder.getAnalysis();
        fillAnalysisBuilder(analysisBuilder);
        AnalysisWriter writer = new AnalysisWriter();
        File folder = new File(this.pathName);
        // if (folder.exists()) {
        // return null;
        // } else {
        TypedReturnCode<File> saved = writer.createAnalysisFile(analysis, folder);
        if (saved.isOk()) {
            log.info("Saved in  " + folder.getAbsolutePath());
            AnaResourceFileHelper.getInstance().setResourceChanged(true);
        } else {
            throw new DataprofilerCoreException("Problem saving file: " + folder.getAbsolutePath() + ": " + saved.getMessage());
        }

        CorePlugin.getDefault().refreshWorkSpace();
        return saved.getObject();

    }

    protected void fillAnalysisBuilder(AnalysisBuilder analysisBuilder) {
        ConnectionAnalysisParameter parameters = (ConnectionAnalysisParameter) getAnalysisParameter();
        String analysisStatue = parameters.getAnalysisStatus();
        String analysisAuthor = parameters.getAnalysisAuthor();
        String analysisPurpse = parameters.getAnalysisPurpose();
        String analysisDescription = parameters.getAnalysisDescription();

        Analysis analysis = analysisBuilder.getAnalysis();
        TaggedValueHelper.setDevStatus(analysis, DevelopmentStatus.get(analysisStatue));
        TaggedValueHelper.setAuthor(analysis, analysisAuthor);
        DescriptionHelper.setPurpose(analysisPurpse, analysis);
        DescriptionHelper.setDescription(analysisDescription, analysis);
    }

    protected ConnectionParameter getAnalysisParameter() {
        return AbstractAnalysisWizardPage.getConnectionParams();
    }
}
