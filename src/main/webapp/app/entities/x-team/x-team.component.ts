import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IXTeam } from 'app/shared/model/x-team.model';
import { Principal } from 'app/core';
import { XTeamService } from './x-team.service';

@Component({
    selector: 'jhi-x-team',
    templateUrl: './x-team.component.html'
})
export class XTeamComponent implements OnInit, OnDestroy {
    xTeams: IXTeam[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private xTeamService: XTeamService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.xTeamService.query().subscribe(
            (res: HttpResponse<IXTeam[]>) => {
                this.xTeams = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInXTeams();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IXTeam) {
        return item.id;
    }

    registerChangeInXTeams() {
        this.eventSubscriber = this.eventManager.subscribe('xTeamListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
