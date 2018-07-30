import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IXTeam } from 'app/shared/model/x-team.model';

@Component({
    selector: 'jhi-x-team-detail',
    templateUrl: './x-team-detail.component.html'
})
export class XTeamDetailComponent implements OnInit {
    xTeam: IXTeam;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ xTeam }) => {
            this.xTeam = xTeam;
        });
    }

    previousState() {
        window.history.back();
    }
}
