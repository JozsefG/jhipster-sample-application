import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IXUser } from 'app/shared/model/x-user.model';
import { XUserService } from './x-user.service';

@Component({
    selector: 'jhi-x-user-update',
    templateUrl: './x-user-update.component.html'
})
export class XUserUpdateComponent implements OnInit {
    private _xUser: IXUser;
    isSaving: boolean;

    constructor(private xUserService: XUserService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ xUser }) => {
            this.xUser = xUser;
        });
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
    get xUser() {
        return this._xUser;
    }

    set xUser(xUser: IXUser) {
        this._xUser = xUser;
    }
}
