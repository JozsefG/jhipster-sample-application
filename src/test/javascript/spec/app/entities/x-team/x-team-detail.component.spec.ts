/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { XTeamDetailComponent } from 'app/entities/x-team/x-team-detail.component';
import { XTeam } from 'app/shared/model/x-team.model';

describe('Component Tests', () => {
    describe('XTeam Management Detail Component', () => {
        let comp: XTeamDetailComponent;
        let fixture: ComponentFixture<XTeamDetailComponent>;
        const route = ({ data: of({ xTeam: new XTeam(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [XTeamDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(XTeamDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(XTeamDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.xTeam).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
