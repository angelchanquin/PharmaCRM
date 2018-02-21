/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PharmacrmTestModule } from '../../../test.module';
import { RecepcionDeCompraDialogComponent } from '../../../../../../main/webapp/app/entities/recepcion-de-compra/recepcion-de-compra-dialog.component';
import { RecepcionDeCompraService } from '../../../../../../main/webapp/app/entities/recepcion-de-compra/recepcion-de-compra.service';
import { RecepcionDeCompra } from '../../../../../../main/webapp/app/entities/recepcion-de-compra/recepcion-de-compra.model';
import { OrdenDeCompraService } from '../../../../../../main/webapp/app/entities/orden-de-compra';

describe('Component Tests', () => {

    describe('RecepcionDeCompra Management Dialog Component', () => {
        let comp: RecepcionDeCompraDialogComponent;
        let fixture: ComponentFixture<RecepcionDeCompraDialogComponent>;
        let service: RecepcionDeCompraService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [RecepcionDeCompraDialogComponent],
                providers: [
                    OrdenDeCompraService,
                    RecepcionDeCompraService
                ]
            })
            .overrideTemplate(RecepcionDeCompraDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecepcionDeCompraDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecepcionDeCompraService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RecepcionDeCompra(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.recepcionDeCompra = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'recepcionDeCompraListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RecepcionDeCompra();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.recepcionDeCompra = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'recepcionDeCompraListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
