/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { XGroupDetailComponent } from 'app/entities/x-group/x-group-detail.component';
import { XGroup } from 'app/shared/model/x-group.model';

describe('Component Tests', () => {
    describe('XGroup Management Detail Component', () => {
        let comp: XGroupDetailComponent;
        let fixture: ComponentFixture<XGroupDetailComponent>;
        const route = ({ data: of({ xGroup: new XGroup(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [XGroupDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(XGroupDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(XGroupDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.xGroup).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
