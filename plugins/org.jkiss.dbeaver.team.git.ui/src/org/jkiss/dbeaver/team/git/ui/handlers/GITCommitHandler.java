/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2019 Serge Rider (serge@jkiss.org)
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

package org.jkiss.dbeaver.team.git.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.internal.commit.CommitUI;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class GITCommitHandler extends GITAbstractHandler {

    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {

        final Repository repository = getRepository(true, event);
        if (repository == null) {
            return null;
        }
        final Shell shell = HandlerUtil.getActiveShell(event);
        IResource[] resourcesInScope = getResourcesInScope(event);
        if (resourcesInScope != null) {
            CommitUI commitUi = new CommitUI(shell, repository,
                    resourcesInScope, false);
            commitUi.commit();
        }
        return null;
    }

}
