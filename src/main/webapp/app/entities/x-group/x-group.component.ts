import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IXGroup } from 'app/shared/model/x-group.model';
import { Principal } from 'app/core';
import { XGroupService } from './x-group.service';

@Component({
    selector: 'jhi-x-group',
    templateUrl: './x-group.component.html'
})
export class XGroupComponent implements OnInit, OnDestroy {
    xGroups: IXGroup[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private xGroupService: XGroupService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.xGroupService.query().subscribe(
            (res: HttpResponse<IXGroup[]>) => {
                this.xGroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInXGroups();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IXGroup) {
        return item.id;
    }

    registerChangeInXGroups() {
        this.eventSubscriber = this.eventManager.subscribe('xGroupListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
