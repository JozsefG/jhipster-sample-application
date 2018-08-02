import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    XGroupComponent,
    XGroupDetailComponent,
    XGroupUpdateComponent,
    XGroupDeletePopupComponent,
    XGroupDeleteDialogComponent,
    xGroupRoute,
    xGroupPopupRoute
} from './';

const ENTITY_STATES = [...xGroupRoute, ...xGroupPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [XGroupComponent, XGroupDetailComponent, XGroupUpdateComponent, XGroupDeleteDialogComponent, XGroupDeletePopupComponent],
    entryComponents: [XGroupComponent, XGroupUpdateComponent, XGroupDeleteDialogComponent, XGroupDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationXGroupModule {}
