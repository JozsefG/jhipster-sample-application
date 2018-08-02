import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { XUser } from 'app/shared/model/x-user.model';
import { XUserService } from './x-user.service';
import { XUserComponent } from './x-user.component';
import { XUserDetailComponent } from './x-user-detail.component';
import { XUserUpdateComponent } from './x-user-update.component';
import { XUserDeletePopupComponent } from './x-user-delete-dialog.component';
import { IXUser } from 'app/shared/model/x-user.model';

@Injectable({ providedIn: 'root' })
export class XUserResolve implements Resolve<IXUser> {
    constructor(private service: XUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((xUser: HttpResponse<XUser>) => xUser.body));
        }
        return of(new XUser());
    }
}

export const xUserRoute: Routes = [
    {
        path: 'x-user',
        component: XUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'x-user/:id/view',
        component: XUserDetailComponent,
        resolve: {
            xUser: XUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'x-user/new',
        component: XUserUpdateComponent,
        resolve: {
            xUser: XUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'x-user/:id/edit',
        component: XUserUpdateComponent,
        resolve: {
            xUser: XUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const xUserPopupRoute: Routes = [
    {
        path: 'x-user/:id/delete',
        component: XUserDeletePopupComponent,
        resolve: {
            xUser: XUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
