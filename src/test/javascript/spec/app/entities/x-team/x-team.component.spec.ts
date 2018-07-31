/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { XTeamComponent } from 'app/entities/x-team/x-team.component';
import { XTeamService } from 'app/entities/x-team/x-team.service';
import { XTeam } from 'app/shared/model/x-team.model';

describe('Component Tests', () => {
    describe('XTeam Management Component', () => {
        let comp: XTeamComponent;
        let fixture: ComponentFixture<XTeamComponent>;
        let service: XTeamService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [XTeamComponent],
                providers: []
            })
                .overrideTemplate(XTeamComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(XTeamComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(XTeamService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new XTeam(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.xTeams[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
