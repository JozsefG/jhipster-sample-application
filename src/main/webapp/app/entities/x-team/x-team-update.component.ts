import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IXTeam } from 'app/shared/model/x-team.model';
import { XTeamService } from './x-team.service';
import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from 'app/entities/organization';

@Component({
    selector: 'jhi-x-team-update',
    templateUrl: './x-team-update.component.html'
})
export class XTeamUpdateComponent implements OnInit {
    private _xTeam: IXTeam;
    isSaving: boolean;

    organizations: IOrganization[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private xTeamService: XTeamService,
        private organizationService: OrganizationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ xTeam }) => {
            this.xTeam = xTeam;
        });
        this.organizationService.query().subscribe(
            (res: HttpResponse<IOrganization[]>) => {
                this.organizations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.xTeam.id !== undefined) {
            this.subscribeToSaveResponse(this.xTeamService.update(this.xTeam));
        } else {
            this.subscribeToSaveResponse(this.xTeamService.create(this.xTeam));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IXTeam>>) {
        result.subscribe((res: HttpResponse<IXTeam>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackOrganizationById(index: number, item: IOrganization) {
        return item.id;
    }
    get xTeam() {
        return this._xTeam;
    }

    set xTeam(xTeam: IXTeam) {
        this._xTeam = xTeam;
    }
}
