import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    XTeamComponent,
    XTeamDetailComponent,
    XTeamUpdateComponent,
    XTeamDeletePopupComponent,
    XTeamDeleteDialogComponent,
    xTeamRoute,
    xTeamPopupRoute
} from './';

const ENTITY_STATES = [...xTeamRoute, ...xTeamPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [XTeamComponent, XTeamDetailComponent, XTeamUpdateComponent, XTeamDeleteDialogComponent, XTeamDeletePopupComponent],
    entryComponents: [XTeamComponent, XTeamUpdateComponent, XTeamDeleteDialogComponent, XTeamDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationXTeamModule {}
