/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { XUserDetailComponent } from 'app/entities/x-user/x-user-detail.component';
import { XUser } from 'app/shared/model/x-user.model';

describe('Component Tests', () => {
    describe('XUser Management Detail Component', () => {
        let comp: XUserDetailComponent;
        let fixture: ComponentFixture<XUserDetailComponent>;
        const route = ({ data: of({ xUser: new XUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [XUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(XUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(XUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.xUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
