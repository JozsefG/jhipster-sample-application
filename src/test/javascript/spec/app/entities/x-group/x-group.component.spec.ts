/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { XGroupComponent } from 'app/entities/x-group/x-group.component';
import { XGroupService } from 'app/entities/x-group/x-group.service';
import { XGroup } from 'app/shared/model/x-group.model';

describe('Component Tests', () => {
    describe('XGroup Management Component', () => {
        let comp: XGroupComponent;
        let fixture: ComponentFixture<XGroupComponent>;
        let service: XGroupService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [XGroupComponent],
                providers: []
            })
                .overrideTemplate(XGroupComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(XGroupComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(XGroupService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new XGroup(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.xGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
