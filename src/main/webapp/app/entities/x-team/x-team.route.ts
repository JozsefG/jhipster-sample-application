import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { XTeam } from 'app/shared/model/x-team.model';
import { XTeamService } from './x-team.service';
import { XTeamComponent } from './x-team.component';
import { XTeamDetailComponent } from './x-team-detail.component';
import { XTeamUpdateComponent } from './x-team-update.component';
import { XTeamDeletePopupComponent } from './x-team-delete-dialog.component';
import { IXTeam } from 'app/shared/model/x-team.model';

@Injectable({ providedIn: 'root' })
export class XTeamResolve implements Resolve<IXTeam> {
    constructor(private service: XTeamService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((xTeam: HttpResponse<XTeam>) => xTeam.body));
        }
        return of(new XTeam());
    }
}

export const xTeamRoute: Routes = [
    {
        path: 'x-team',
        component: XTeamComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XTeams'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'x-team/:id/view',
        component: XTeamDetailComponent,
        resolve: {
            xTeam: XTeamResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XTeams'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'x-team/new',
        component: XTeamUpdateComponent,
        resolve: {
            xTeam: XTeamResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XTeams'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'x-team/:id/edit',
        component: XTeamUpdateComponent,
        resolve: {
            xTeam: XTeamResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XTeams'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const xTeamPopupRoute: Routes = [
    {
        path: 'x-team/:id/delete',
        component: XTeamDeletePopupComponent,
        resolve: {
            xTeam: XTeamResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'XTeams'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
