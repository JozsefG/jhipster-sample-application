import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IXGroup } from 'app/shared/model/x-group.model';

@Component({
    selector: 'jhi-x-group-detail',
    templateUrl: './x-group-detail.component.html'
})
export class XGroupDetailComponent implements OnInit {
    xGroup: IXGroup;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ xGroup }) => {
            this.xGroup = xGroup;
        });
    }

    previousState() {
        window.history.back();
    }
}
