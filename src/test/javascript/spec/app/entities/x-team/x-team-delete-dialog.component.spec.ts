/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { XTeamDeleteDialogComponent } from 'app/entities/x-team/x-team-delete-dialog.component';
import { XTeamService } from 'app/entities/x-team/x-team.service';

describe('Component Tests', () => {
    describe('XTeam Management Delete Component', () => {
        let comp: XTeamDeleteDialogComponent;
        let fixture: ComponentFixture<XTeamDeleteDialogComponent>;
        let service: XTeamService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [XTeamDeleteDialogComponent]
            })
                .overrideTemplate(XTeamDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(XTeamDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(XTeamService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
