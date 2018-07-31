import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IXUser } from 'app/shared/model/x-user.model';
import { Principal } from 'app/core';
import { XUserService } from './x-user.service';

@Component({
    selector: 'jhi-x-user',
    templateUrl: './x-user.component.html'
})
export class XUserComponent implements OnInit, OnDestroy {
    xUsers: IXUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private xUserService: XUserService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.xUserService.query().subscribe(
            (res: HttpResponse<IXUser[]>) => {
                this.xUsers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInXUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IXUser) {
        return item.id;
    }

    registerChangeInXUsers() {
        this.eventSubscriber = this.eventManager.subscribe('xUserListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
