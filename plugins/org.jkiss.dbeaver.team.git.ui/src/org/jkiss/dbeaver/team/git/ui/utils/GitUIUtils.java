/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2019 Serge Rider (serge@jkiss.org)
 * Copyright (C) 2019 Andrew Khitrin (ahitrin@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jkiss.dbeaver.team.git.ui.utils;


import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.jkiss.dbeaver.model.navigator.*;
import org.jkiss.dbeaver.ui.editors.EditorUtils;
import org.jkiss.dbeaver.ui.editors.IDatabaseEditorInput;

public class GitUIUtils {

    public static IProject extractActiveProject(ExecutionEvent event) {
        IProject project = GitUIUtils.extractProject(HandlerUtil.getCurrentSelection(event));
        if (project == null) {
            IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
            if (activeEditor != null) {
                IEditorInput editorInput = activeEditor.getEditorInput();
                if (editorInput instanceof IDatabaseEditorInput) {
                    DBNDatabaseNode node = ((IDatabaseEditorInput) editorInput).getNavigatorNode();
                    if (node != null) {
                        return node.getOwnerProject();
                    }
                } else {
                    IFile input = EditorUtils.getFileFromInput(editorInput);
                    if (input != null) {
                        project = input.getProject();
                    }
                }
            }
        }
        return project;
    }

    public static IProject extractProject(ISelection selection) {
        if (selection != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
            Object e = ((IStructuredSelection) selection).getFirstElement();
            if (e instanceof DBNResource) {
                IResource resource = ((DBNResource) e).getResource();
                return resource == null ? null : resource.getProject();
            } else if (e instanceof DBNNode) {
                for (DBNNode n = (DBNNode) e; n != null; n = ((DBNNode) n).getParentNode()) {
                    if (n instanceof DBNProject) {
                        return ((DBNProject) n).getProject();
                    }
                }
                return null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
