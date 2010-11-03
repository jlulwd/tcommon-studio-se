// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend � www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later
// version.
//
// This library is distributed in the hope that it will be
// useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty
// of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public
// License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.swtbot;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.matchers.AbstractMatcher;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.BeforeClass;
import org.talend.core.model.process.INode;
import org.talend.core.properties.tab.TalendTabbedPropertyList;
import org.talend.designer.core.ui.editor.connections.ConnLabelEditPart;
import org.talend.designer.core.ui.editor.connections.ConnectionLabel;
import org.talend.designer.core.ui.editor.nodes.NodePart;

/**
 * DOC sgandon class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 1 2006-09-29 17:06:40Z nrousseau $
 * 
 */
public class TalendSwtBotForTos {

    static public SWTGefBot gefBot = new SWTGefBot();

    static private boolean isGenerationEngineInitialised = false;

    /**
     * wait for the Generation engine to be intialised, and this is done only once during the lifetime of the
     * application.
     */
    @BeforeClass
    public static void before() {
        if (!isGenerationEngineInitialised) {
            gefBot.waitUntil(Conditions.shellIsActive("Generation Engine Initialization in progress..."));
            SWTBotShell shell = gefBot.shell("Generation Engine Initialization in progress...");
            gefBot.waitUntil(Conditions.shellCloses(shell), 60000 * 5);
            gefBot.viewByTitle("Welcome").close();
            gefBot.menu("Window").menu("Perspective").menu("Design Workspace").click();
            isGenerationEngineInitialised = true;
        }
    }

    /**
     * return the first talend component EditPart (NodePart) that relates to the label componentLabel for a given
     * editor.
     * 
     * @param gefEditor the editor to look in for the component
     * @param componentLabel the displayed label of the component
     * @return the component found with the given label or null if none found
     */
    public SWTBotGefEditPart getTalendComponentPart(SWTBotGefEditor gefEditor, final String componentLabel) {
        List<SWTBotGefEditPart> editParts = gefEditor.editParts(new AbstractMatcher<EditPart>() {

            protected boolean doMatch(Object item) {
                boolean result = false;
                if (item instanceof NodePart) {
                    // get the parent and look for one children of type NodeLabelEditPart that has a figure named
                    // componentName
                    EditPart np = (EditPart) item;
                    INode node = (INode) np.getModel();
                    result = componentLabel.equals(node.getLabel());
                } else if (item instanceof ConnLabelEditPart) {
                    // get the parent and look for one children of type NodeLabelEditPart that has a figure named
                    // componentName
                    EditPart np = (EditPart) item;
                    ConnectionLabel cl = (ConnectionLabel) np.getModel();
                    result = componentLabel.equals(cl.getLabelText());
                }// else return default result that is false
                return result;
            }

            public void describeTo(Description description) {
                description.appendText("NodePart with NodeLabelEditPart figure named (").appendText(componentLabel).appendText(
                        ")");
            }
        });
        return editParts.size() > 0 ? editParts.get(0) : null;
    }

    /**
     * look for all the widget of type TalendTabbedPropertyList and select the tab at index for all of them
     * 
     * @param index index of the tab to select in aTalendTabbedPropertyList TalendTabbedPropertyList(values start from
     * 0)
     */
    public void selecteAllTalendTabbedPropertyListIndex(final int index) {
        // look for all the widgets of type TalendTabbedPropertyList
        final List<TalendTabbedPropertyList> controls = gefBot.getFinder().findControls(
                new BaseMatcher<TalendTabbedPropertyList>() {

                    // @Override
                    public boolean matches(Object item) {
                        if (item instanceof TalendTabbedPropertyList) {
                            return true;
                        } else {
                            return false;
                        }
                    }

                    // @Override
                    public void describeTo(Description description) {
                        description.appendText("looking for widget of type TalendTabbedPropertyList");
                    }
                });

        // select the desired tab in the UI thread
        UIThreadRunnable.syncExec(new VoidResult() {

            // @Override
            public void run() {
                for (TalendTabbedPropertyList ttpl : controls) {
                    ttpl.select(index);
                }
            }
        });
    }

}
