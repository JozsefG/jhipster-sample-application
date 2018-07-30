import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    XUserComponent,
    XUserDetailComponent,
    XUserUpdateComponent,
    XUserDeletePopupComponent,
    XUserDeleteDialogComponent,
    xUserRoute,
    xUserPopupRoute
} from './';

const ENTITY_STATES = [...xUserRoute, ...xUserPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [XUserComponent, XUserDetailComponent, XUserUpdateComponent, XUserDeleteDialogComponent, XUserDeletePopupComponent],
    entryComponents: [XUserComponent, XUserUpdateComponent, XUserDeleteDialogComponent, XUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationXUserModule {}
