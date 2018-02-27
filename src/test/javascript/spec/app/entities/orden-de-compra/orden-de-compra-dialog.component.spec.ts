``/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PharmacrmTestModule } from '../../../test.module';
import { OrdenDeCompraDialogComponent } from '../../../../../../main/webapp/app/entities/orden-de-compra/orden-de-compra-dialog.component';
import { OrdenDeCompraService } from '../../../../../../main/webapp/app/entities/orden-de-compra/orden-de-compra.service';
import { OrdenDeCompra } from '../../../../../../main/webapp/app/entities/orden-de-compra/orden-de-compra.model';
import { ProveedorService } from '../../../../../../main/webapp/app/entities/proveedor';

describe('Component Tests', () => {

    describe('OrdenDeCompra Management Dialog Component', () => {
        let comp: OrdenDeCompraDialogComponent;
        let fixture: ComponentFixture<OrdenDeCompraDialogComponent>;
        let service: OrdenDeCompraService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [OrdenDeCompraDialogComponent],
                providers: [
                    ProveedorService,
                    OrdenDeCompraService
                ]
            })
            .overrideTemplate(OrdenDeCompraDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdenDeCompraDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdenDeCompraService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OrdenDeCompra(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.ordenDeCompra = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ordenDeCompraListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OrdenDeCompra();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.ordenDeCompra = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ordenDeCompraListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
