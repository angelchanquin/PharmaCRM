/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PharmacrmTestModule } from '../../../test.module';
import { InventarioDialogComponent } from '../../../../../../main/webapp/app/entities/inventario/inventario-dialog.component';
import { InventarioService } from '../../../../../../main/webapp/app/entities/inventario/inventario.service';
import { Inventario } from '../../../../../../main/webapp/app/entities/inventario/inventario.model';
import { ProductoService } from '../../../../../../main/webapp/app/entities/producto';

describe('Component Tests', () => {

    describe('Inventario Management Dialog Component', () => {
        let comp: InventarioDialogComponent;
        let fixture: ComponentFixture<InventarioDialogComponent>;
        let service: InventarioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [InventarioDialogComponent],
                providers: [
                    ProductoService,
                    InventarioService
                ]
            })
            .overrideTemplate(InventarioDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InventarioDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventarioService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Inventario(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.inventario = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'inventarioListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Inventario();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.inventario = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'inventarioListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
