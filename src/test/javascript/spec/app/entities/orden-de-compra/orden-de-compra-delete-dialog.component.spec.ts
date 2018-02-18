/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PharmacrmTestModule } from '../../../test.module';
import { OrdenDeCompraDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/orden-de-compra/orden-de-compra-delete-dialog.component';
import { OrdenDeCompraService } from '../../../../../../main/webapp/app/entities/orden-de-compra/orden-de-compra.service';

describe('Component Tests', () => {

    describe('OrdenDeCompra Management Delete Component', () => {
        let comp: OrdenDeCompraDeleteDialogComponent;
        let fixture: ComponentFixture<OrdenDeCompraDeleteDialogComponent>;
        let service: OrdenDeCompraService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [OrdenDeCompraDeleteDialogComponent],
                providers: [
                    OrdenDeCompraService
                ]
            })
            .overrideTemplate(OrdenDeCompraDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdenDeCompraDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdenDeCompraService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
