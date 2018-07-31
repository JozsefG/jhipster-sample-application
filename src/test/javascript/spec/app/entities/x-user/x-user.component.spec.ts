/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { XUserComponent } from 'app/entities/x-user/x-user.component';
import { XUserService } from 'app/entities/x-user/x-user.service';
import { XUser } from 'app/shared/model/x-user.model';

describe('Component Tests', () => {
    describe('XUser Management Component', () => {
        let comp: XUserComponent;
        let fixture: ComponentFixture<XUserComponent>;
        let service: XUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [XUserComponent],
                providers: []
            })
                .overrideTemplate(XUserComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(XUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(XUserService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new XUser(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.xUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
