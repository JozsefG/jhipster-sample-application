/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { XGroupUpdateComponent } from 'app/entities/x-group/x-group-update.component';
import { XGroupService } from 'app/entities/x-group/x-group.service';
import { XGroup } from 'app/shared/model/x-group.model';

describe('Component Tests', () => {
    describe('XGroup Management Update Component', () => {
        let comp: XGroupUpdateComponent;
        let fixture: ComponentFixture<XGroupUpdateComponent>;
        let service: XGroupService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [XGroupUpdateComponent]
            })
                .overrideTemplate(XGroupUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(XGroupUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(XGroupService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new XGroup(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.xGroup = entity;
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
                    const entity = new XGroup();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.xGroup = entity;
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
