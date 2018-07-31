import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IXUser } from 'app/shared/model/x-user.model';
import { XUserService } from './x-user.service';
import { IXGroup } from 'app/shared/model/x-group.model';
import { XGroupService } from 'app/entities/x-group';

@Component({
    selector: 'jhi-x-user-update',
    templateUrl: './x-user-update.component.html'
})
export class XUserUpdateComponent implements OnInit {
    private _xUser: IXUser;
    isSaving: boolean;

    xgroups: IXGroup[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private xUserService: XUserService,
        private xGroupService: XGroupService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ xUser }) => {
            this.xUser = xUser;
        });
        this.xGroupService.query().subscribe(
            (res: HttpResponse<IXGroup[]>) => {
                this.xgroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.xUser.id !== undefined) {
            this.subscribeToSaveResponse(this.xUserService.update(this.xUser));
        } else {
            this.subscribeToSaveResponse(this.xUserService.create(this.xUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IXUser>>) {
        result.subscribe((res: HttpResponse<IXUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackXGroupById(index: number, item: IXGroup) {
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
    get xUser() {
        return this._xUser;
    }

    set xUser(xUser: IXUser) {
        this._xUser = xUser;
    }
}
