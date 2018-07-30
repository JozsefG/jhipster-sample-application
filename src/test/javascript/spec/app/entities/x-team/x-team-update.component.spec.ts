/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { XTeamUpdateComponent } from 'app/entities/x-team/x-team-update.component';
import { XTeamService } from 'app/entities/x-team/x-team.service';
import { XTeam } from 'app/shared/model/x-team.model';

describe('Component Tests', () => {
    describe('XTeam Management Update Component', () => {
        let comp: XTeamUpdateComponent;
        let fixture: ComponentFixture<XTeamUpdateComponent>;
        let service: XTeamService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [XTeamUpdateComponent]
            })
                .overrideTemplate(XTeamUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(XTeamUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(XTeamService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new XTeam(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.xTeam = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new XTeam();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.xTeam = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
