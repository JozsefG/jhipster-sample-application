import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { XGroup } from 'app/shared/model/x-group.model';
import { XGroupService } from './x-group.service';
import { XGroupComponent } from './x-group.component';
import { XGroupDetailComponent } from './x-group-detail.component';
import { XGroupUpdateComponent } from './x-group-update.component';
import { XGroupDeletePopupComponent } from './x-group-delete-dialog.component';
import { IXGroup } from 'app/shared/model/x-group.model';

@Injectable({ providedIn: 'root' })
export class XGroupResolve implements Resolve<IXGroup> {
    constructor(private service: XGroupService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((xGroup: HttpResponse<XGroup>) => xGroup.body));
        }
        return of(new XGroup());
    }
}

export const xGroupRoute: Routes = [
    {
        path: 'x-group',
        component: XGroupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'x-group/:id/view',
        component: XGroupDetailComponent,
        resolve: {
            xGroup: XGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'x-group/new',
        component: XGroupUpdateComponent,
        resolve: {
            xGroup: XGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'x-group/:id/edit',
        component: XGroupUpdateComponent,
        resolve: {
            xGroup: XGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XGroups'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const xGroupPopupRoute: Routes = [
    {
        path: 'x-group/:id/delete',
        component: XGroupDeletePopupComponent,
        resolve: {
            xGroup: XGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
