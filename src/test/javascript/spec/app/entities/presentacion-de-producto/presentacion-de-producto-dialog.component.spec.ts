/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PharmacrmTestModule } from '../../../test.module';
import { PresentacionDeProductoDialogComponent } from '../../../../../../main/webapp/app/entities/presentacion-de-producto/presentacion-de-producto-dialog.component';
import { PresentacionDeProductoService } from '../../../../../../main/webapp/app/entities/presentacion-de-producto/presentacion-de-producto.service';
import { PresentacionDeProducto } from '../../../../../../main/webapp/app/entities/presentacion-de-producto/presentacion-de-producto.model';

describe('Component Tests', () => {

    describe('PresentacionDeProducto Management Dialog Component', () => {
        let comp: PresentacionDeProductoDialogComponent;
        let fixture: ComponentFixture<PresentacionDeProductoDialogComponent>;
        let service: PresentacionDeProductoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [PresentacionDeProductoDialogComponent],
                providers: [
                    PresentacionDeProductoService
                ]
            })
            .overrideTemplate(PresentacionDeProductoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PresentacionDeProductoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PresentacionDeProductoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PresentacionDeProducto(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.presentacionDeProducto = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'presentacionDeProductoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PresentacionDeProducto();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.presentacionDeProducto = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'presentacionDeProductoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
