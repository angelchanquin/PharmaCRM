import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('OrdenDeCompra e2e test', () => {

    let navBarPage: NavBarPage;
    let ordenDeCompraDialogPage: OrdenDeCompraDialogPage;
    let ordenDeCompraComponentsPage: OrdenDeCompraComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load OrdenDeCompras', () => {
        navBarPage.goToEntity('orden-de-compra');
        ordenDeCompraComponentsPage = new OrdenDeCompraComponentsPage();
        expect(ordenDeCompraComponentsPage.getTitle())
            .toMatch(/pharmacrmApp.ordenDeCompra.home.title/);

    });

    it('should load create OrdenDeCompra dialog', () => {
        ordenDeCompraComponentsPage.clickOnCreateButton();
        ordenDeCompraDialogPage = new OrdenDeCompraDialogPage();
        expect(ordenDeCompraDialogPage.getModalTitle())
            .toMatch(/pharmacrmApp.ordenDeCompra.home.createOrEditLabel/);
        ordenDeCompraDialogPage.close();
    });

   /* it('should create and save OrdenDeCompras', () => {
        ordenDeCompraComponentsPage.clickOnCreateButton();
        ordenDeCompraDialogPage.setNumeroDeReferenciaInput('numeroDeReferencia');
        expect(ordenDeCompraDialogPage.getNumeroDeReferenciaInput()).toMatch('numeroDeReferencia');
        ordenDeCompraDialogPage.setFechaInput('2000-12-31');
        expect(ordenDeCompraDialogPage.getFechaInput()).toMatch('2000-12-31');
        ordenDeCompraDialogPage.setTotalInput('5');
        expect(ordenDeCompraDialogPage.getTotalInput()).toMatch('5');
        ordenDeCompraDialogPage.setFechaDeEntregaEsperadaInput('2000-12-31');
        expect(ordenDeCompraDialogPage.getFechaDeEntregaEsperadaInput()).toMatch('2000-12-31');
        ordenDeCompraDialogPage.estadoSelectLastOption();
        ordenDeCompraDialogPage.recibidoSelectLastOption();
        ordenDeCompraDialogPage.proveedorSelectLastOption();
        ordenDeCompraDialogPage.save();
        expect(ordenDeCompraDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OrdenDeCompraComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-orden-de-compra div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class OrdenDeCompraDialogPage {
    modalTitle = element(by.css('h4#myOrdenDeCompraLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    numeroDeReferenciaInput = element(by.css('input#field_numeroDeReferencia'));
    fechaInput = element(by.css('input#field_fecha'));
    totalInput = element(by.css('input#field_total'));
    fechaDeEntregaEsperadaInput = element(by.css('input#field_fechaDeEntregaEsperada'));
    estadoSelect = element(by.css('select#field_estado'));
    recibidoSelect = element(by.css('select#field_recibido'));
    proveedorSelect = element(by.css('select#field_proveedor'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNumeroDeReferenciaInput = function(numeroDeReferencia) {
        this.numeroDeReferenciaInput.sendKeys(numeroDeReferencia);
    }

    getNumeroDeReferenciaInput = function() {
        return this.numeroDeReferenciaInput.getAttribute('value');
    }

    setFechaInput = function(fecha) {
        this.fechaInput.sendKeys(fecha);
    }

    getFechaInput = function() {
        return this.fechaInput.getAttribute('value');
    }

    setTotalInput = function(total) {
        this.totalInput.sendKeys(total);
    }

    getTotalInput = function() {
        return this.totalInput.getAttribute('value');
    }

    setFechaDeEntregaEsperadaInput = function(fechaDeEntregaEsperada) {
        this.fechaDeEntregaEsperadaInput.sendKeys(fechaDeEntregaEsperada);
    }

    getFechaDeEntregaEsperadaInput = function() {
        return this.fechaDeEntregaEsperadaInput.getAttribute('value');
    }

    setEstadoSelect = function(estado) {
        this.estadoSelect.sendKeys(estado);
    }

    getEstadoSelect = function() {
        return this.estadoSelect.element(by.css('option:checked')).getText();
    }

    estadoSelectLastOption = function() {
        this.estadoSelect.all(by.tagName('option')).last().click();
    }
    setRecibidoSelect = function(recibido) {
        this.recibidoSelect.sendKeys(recibido);
    }

    getRecibidoSelect = function() {
        return this.recibidoSelect.element(by.css('option:checked')).getText();
    }

    recibidoSelectLastOption = function() {
        this.recibidoSelect.all(by.tagName('option')).last().click();
    }
    proveedorSelectLastOption = function() {
        this.proveedorSelect.all(by.tagName('option')).last().click();
    }

    proveedorSelectOption = function(option) {
        this.proveedorSelect.sendKeys(option);
    }

    getProveedorSelect = function() {
        return this.proveedorSelect;
    }

    getProveedorSelectedOption = function() {
        return this.proveedorSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
