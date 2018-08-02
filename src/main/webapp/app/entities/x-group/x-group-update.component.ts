import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IXGroup } from 'app/shared/model/x-group.model';
import { XGroupService } from './x-group.service';
import { IXUser } from 'app/shared/model/x-user.model';
import { XUserService } from 'app/entities/x-user';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team';

@Component({
    selector: 'jhi-x-group-update',
    templateUrl: './x-group-update.component.html'
})
export class XGroupUpdateComponent implements OnInit {
    private _xGroup: IXGroup;
    isSaving: boolean;

    xusers: IXUser[];

    teams: ITeam[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private xGroupService: XGroupService,
        private xUserService: XUserService,
        private teamService: TeamService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ xGroup }) => {
            this.xGroup = xGroup;
        });
        this.xUserService.query().subscribe(
            (res: HttpResponse<IXUser[]>) => {
                this.xusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.teamService.query().subscribe(
            (res: HttpResponse<ITeam[]>) => {
                this.teams = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.xGroup.id !== undefined) {
            this.subscribeToSaveResponse(this.xGroupService.update(this.xGroup));
        } else {
            this.subscribeToSaveResponse(this.xGroupService.create(this.xGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IXGroup>>) {
        result.subscribe((res: HttpResponse<IXGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackXUserById(index: number, item: IXUser) {
        return item.id;
    }

    trackTeamById(index: number, item: ITeam) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get xGroup() {
        return this._xGroup;
    }

    set xGroup(xGroup: IXGroup) {
        this._xGroup = xGroup;
    }
}
