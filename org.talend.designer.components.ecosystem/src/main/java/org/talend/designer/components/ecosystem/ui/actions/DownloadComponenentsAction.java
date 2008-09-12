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
package org.talend.designer.components.ecosystem.ui.actions;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.components.ComponentUtilities;
import org.talend.designer.codegen.ICodeGeneratorService;
import org.talend.designer.components.ecosystem.EcosystemComponentsProvider;
import org.talend.designer.components.ecosystem.EcosystemConstants;
import org.talend.designer.components.ecosystem.EcosystemUtils;
import org.talend.designer.components.ecosystem.jobs.ComponentDownloader;
import org.talend.designer.components.ecosystem.jobs.ComponentInstaller;
import org.talend.designer.components.ecosystem.jobs.DownloadListener;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.ui.views.EcosystemView;
import org.talend.designer.core.ui.AbstractMultiPageTalendEditor;
import org.talend.designer.core.ui.editor.AbstractTalendEditor;

/**
 * View action for downloading components.
 */
public class DownloadComponenentsAction implements IViewActionDelegate {

    // private static final String SET_FOLDER_TITLE =
    // Messages.getString("DownloadComponenentsAction.SetUserFolder.Title"); //$NON-NLS-1$

    // private static final String SET_FOLDER_MESSAGE =
    // Messages.getString("DownloadComponenentsAction.SetUserFolder.Message"); //$NON-NLS-1$

    private EcosystemView fView;

    private int fExtensionDownloaded;

    private List<ComponentExtension> fInstalledComponents;

    public void init(IViewPart view) {
        fView = (EcosystemView) view;
    }

    public void run(final IAction action) {
        // check if user have set the folder in preference page
        // if (EcosystemUtils.getUserComponentFolder() == null) {
        // MessageDialog.openError(fView.getSite().getShell(), SET_FOLDER_TITLE, SET_FOLDER_MESSAGE);
        // EcosystemUtils.showComponentPreferencePage(fView.getSite().getShell());
        // return;
        // }

        // avoid starting multiple action at the same time.
        action.setEnabled(false);
        try {
            Job job = new DownloadJob(fView.getSelectedExtensions());
            fExtensionDownloaded = 0;
            fInstalledComponents = new ArrayList<ComponentExtension>();
            job.addJobChangeListener(new JobChangeAdapter() {

                @Override
                public void done(final IJobChangeEvent event) {

                    Display.getDefault().asyncExec(new Runnable() {

                        public void run() {
                            updateUI(action, event);
                        }
                    });
                }
            });
            EcosystemUtils.scheduleUserJob(job);

        } catch (Throwable e) {
            ExceptionHandler.process(e);
        }
    }

    /**
     * Update ui after job finished.
     * 
     * @param action
     * @param event
     */
    private void updateUI(final IAction action, final IJobChangeEvent event) {
        // activate aection again after job finished
        action.setEnabled(true);
        if (fExtensionDownloaded > 0) {
            fView.refresh(); // refresh table
            EcosystemUtils.reloadComponents(); // refresh palette
            fView.saveToFile();

            // see feature 0005050: confirmation popup once the component is installed
            confirmInstallation();

            // Start Code Generation Init
            ICodeGeneratorService codeGenService = (ICodeGeneratorService) GlobalServiceRegister.getDefault().getService(
                    ICodeGeneratorService.class);
            Job job = codeGenService.initializeTemplates();
            job.addJobChangeListener(new JobChangeAdapter() {

                @Override
                public void done(IJobChangeEvent event) {

                    ComponentUtilities.setSkipUpdatePalette(false);
                }

            });
        }
    }

    /**
     * DOC chuang Comment method "confirmInstallation".
     */
    private void confirmInstallation() {
        FileFilter propertiesFilter = new FileFilter() {

            public boolean accept(File file) {
                return file.getName().endsWith("_messages.properties");
            }
        };
        String componentName = null;
        Shell shell = this.fView.getSite().getShell();
        StringBuilder message = new StringBuilder();
        for (ComponentExtension component : fInstalledComponents) {
            String location = component.getInstalledLocation();
            File folder = EcosystemComponentsProvider.searchComponentFolder(new File(location));
            File[] files = folder.listFiles(propertiesFilter);
            if (files == null) {
                continue;
            }
            // load property file
            Properties prop = new Properties();
            try {
                prop.load(new FileInputStream(files[0]));
                String name = prop.getProperty("NAME");
                String family = prop.getProperty("FAMILY");
                if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(family)) {
                    componentName = name;
                    message.append("Component ").append(name).append(" installed at ").append(family).append(".\n");
                }
            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
        }
        if (componentName != null) {
            // see 0005051: [ecosystem view] select component in the palette once component is installed
            selectPaletteEntry(componentName);
        }

        MessageDialog.openInformation(shell, "Installed Ecosystem Components", message.toString());

    }

    private void selectPaletteEntry(String componentName) {
        IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (activeWorkbenchWindow != null) {
            IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
            if (activePage != null) {
                IEditorReference[] editorReferences = activePage.getEditorReferences();
                if (editorReferences != null) {
                    for (IEditorReference er : editorReferences) {
                        IEditorPart part = er.getEditor(false);
                        if (part instanceof AbstractMultiPageTalendEditor) {
                            AbstractMultiPageTalendEditor editor = (AbstractMultiPageTalendEditor) part;
                            AbstractTalendEditor talendEditor = editor.getTalendEditor();
                            try {
                                ComponentUtilities.setSkipUpdatePalette(true);
                                talendEditor.selectPaletteEntry(componentName);
                            } catch (Exception e) {
                                ExceptionHandler.process(e);
                            }
                            return;
                        }
                    }
                }
            }
        }

    }

    /**
     * Notify after download complete.
     * 
     * @param extension
     */
    void extensionDownloadCompleted(ComponentExtension extension) {
        fView.addInstalledExtension(extension);
        fExtensionDownloaded++;
        fInstalledComponents.add(extension);
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }

    class DownloadJob extends Job implements DownloadListener {

        private IProgressMonitor fMonitor = null;

        private String fProgressLabel;

        private int fBytesDownloaded;

        private List<ComponentExtension> fExtensions;

        public DownloadJob(List<ComponentExtension> extensions) {
            super(EcosystemConstants.DOWNLOAD_TASK_TITLE);
            fExtensions = extensions;
        }

        @Override
        protected IStatus run(IProgressMonitor monitor) {
            SubMonitor progress = SubMonitor.convert(monitor, fExtensions.size() * 10 + 5);
            progress.setTaskName(this.getName());
            for (ComponentExtension extension : fExtensions) {
                if (progress.isCanceled()) {
                    return Status.CANCEL_STATUS;
                }
                fMonitor = progress.newChild(10);
                downloadExtension(extension, fMonitor);
            }
            progress.setTaskName(EcosystemConstants.RELOAD_PALETTE);
            // progress.done();
            return Status.OK_STATUS;
        }

        private void downloadExtension(ComponentExtension extension, IProgressMonitor monitor) {

            // get the latest revision url
            String componentUrl = extension.getLatestRevision().getUrl();
            monitor.setTaskName(EcosystemConstants.DOWNLOAD_TASK_NAME + componentUrl);
            String targetFolder = EcosystemUtils.getComponentFolder().getAbsolutePath();
            try {
                String fileName = extension.getLatestRevision().getFileName();
                // fileName = extension.getLatestRevision().getFileName();
                File localZipFile = new File(targetFolder, fileName);

                if (extension.getInstalledLocation() != null && extension.getInstalledRevision() != null) {
                    // if already install the latest revision, ignore
                    if (extension.getInstalledRevision().getName().equals(extension.getLatestRevision().getName())) {
                        if (localZipFile.exists() && checkIfExisted(extension.getInstalledLocation())) {
                            monitor.done();
                            return;
                        }
                    } else {
                        // before installing the new revision, delete the older revision that has been installed
                        FileUtils.deleteDirectory(new File(extension.getInstalledLocation()));
                        extension.setInstalledLocation(null);
                        extension.setInstalledRevision(null);
                        fView.removeInstalledExtension(extension);
                    }
                }

                URL url = new URL(componentUrl);

                monitor.setTaskName(EcosystemConstants.DOWNLOAD_TASK_NAME + url.toString());
                ComponentDownloader downloader = new ComponentDownloader();
                downloader.addDownloadListener(this);
                // block until download complete
                downloader.download(url, localZipFile);

                // check if the job is cancelled
                if (!monitor.isCanceled()) {
                    File installedLocation = ComponentInstaller.unzip(localZipFile.getAbsolutePath(), targetFolder);
                    // update extesion status
                    extension.setInstalledRevision(extension.getLatestRevision());
                    extension.setInstalledLocation(installedLocation.getAbsolutePath());
                    monitor.done();
                    extensionDownloadCompleted(extension);
                }
                // the component zip file
                // localZipFile.delete();
            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
        }

        /**
         * Check if the component folder really exist, as the user may delete the folder from filesystem.
         * 
         * @param installedLocation
         * @return
         */
        private boolean checkIfExisted(String installedLocation) {
            try {
                File dir = new File(installedLocation);
                if (dir.exists()) {
                    return true;
                }
            } catch (Throwable e) {
                // do nothing;
            }
            return false;
        }

        public void downloadComplete() {
        }

        public void downloadProgress(ComponentDownloader downloader, int bytesRead) {
            if (fMonitor.isCanceled()) {
                // cancel download
                downloader.setCancel(true);
                return;
            }
            fBytesDownloaded += bytesRead;
            fMonitor.setTaskName(toKbFormat(fBytesDownloaded) + fProgressLabel);
            fMonitor.worked(bytesRead);
        }

        public void downloadStart(int totalSize) {
            fProgressLabel = "/" + toKbFormat(totalSize);
            fBytesDownloaded = 0;
            fMonitor.beginTask("0 KB" + fProgressLabel, totalSize);
        }

        private String toKbFormat(int size) {
            return String.format("%1$s KB", size >> 10); //$NON-NLS-1$
        }
    }
}
