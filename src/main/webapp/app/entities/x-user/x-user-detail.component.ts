import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IXUser } from 'app/shared/model/x-user.model';

@Component({
    selector: 'jhi-x-user-detail',
    templateUrl: './x-user-detail.component.html'
})
export class XUserDetailComponent implements OnInit {
    xUser: IXUser;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ xUser }) => {
            this.xUser = xUser;
        });
    }

    previousState() {
        window.history.back();
    }
}
