import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('DetalleDeRecepcionDeCompra e2e test', () => {

    let navBarPage: NavBarPage;
    let detalleDeRecepcionDeCompraDialogPage: DetalleDeRecepcionDeCompraDialogPage;
    let detalleDeRecepcionDeCompraComponentsPage: DetalleDeRecepcionDeCompraComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load DetalleDeRecepcionDeCompras', () => {
        navBarPage.goToEntity('detalle-de-recepcion-de-compra');
        detalleDeRecepcionDeCompraComponentsPage = new DetalleDeRecepcionDeCompraComponentsPage();
        expect(detalleDeRecepcionDeCompraComponentsPage.getTitle())
            .toMatch(/pharmacrmApp.detalleDeRecepcionDeCompra.home.title/);

    });

    it('should load create DetalleDeRecepcionDeCompra dialog', () => {
        detalleDeRecepcionDeCompraComponentsPage.clickOnCreateButton();
        detalleDeRecepcionDeCompraDialogPage = new DetalleDeRecepcionDeCompraDialogPage();
        expect(detalleDeRecepcionDeCompraDialogPage.getModalTitle())
            .toMatch(/pharmacrmApp.detalleDeRecepcionDeCompra.home.createOrEditLabel/);
        detalleDeRecepcionDeCompraDialogPage.close();
    });

   /* it('should create and save DetalleDeRecepcionDeCompras', () => {
        detalleDeRecepcionDeCompraComponentsPage.clickOnCreateButton();
        detalleDeRecepcionDeCompraDialogPage.setCantidadOrdenadaInput('5');
        expect(detalleDeRecepcionDeCompraDialogPage.getCantidadOrdenadaInput()).toMatch('5');
        detalleDeRecepcionDeCompraDialogPage.setCantidadRecibidaInput('5');
        expect(detalleDeRecepcionDeCompraDialogPage.getCantidadRecibidaInput()).toMatch('5');
        detalleDeRecepcionDeCompraDialogPage.setPrecioInput('5');
        expect(detalleDeRecepcionDeCompraDialogPage.getPrecioInput()).toMatch('5');
        detalleDeRecepcionDeCompraDialogPage.setNoDeLoteInput('noDeLote');
        expect(detalleDeRecepcionDeCompraDialogPage.getNoDeLoteInput()).toMatch('noDeLote');
        detalleDeRecepcionDeCompraDialogPage.setFechaDeVencimientoInput('2000-12-31');
        expect(detalleDeRecepcionDeCompraDialogPage.getFechaDeVencimientoInput()).toMatch('2000-12-31');
        detalleDeRecepcionDeCompraDialogPage.productoSelectLastOption();
        detalleDeRecepcionDeCompraDialogPage.recepcionDeCompraSelectLastOption();
        detalleDeRecepcionDeCompraDialogPage.save();
        expect(detalleDeRecepcionDeCompraDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DetalleDeRecepcionDeCompraComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-detalle-de-recepcion-de-compra div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DetalleDeRecepcionDeCompraDialogPage {
    modalTitle = element(by.css('h4#myDetalleDeRecepcionDeCompraLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    cantidadOrdenadaInput = element(by.css('input#field_cantidadOrdenada'));
    cantidadRecibidaInput = element(by.css('input#field_cantidadRecibida'));
    precioInput = element(by.css('input#field_precio'));
    noDeLoteInput = element(by.css('input#field_noDeLote'));
    fechaDeVencimientoInput = element(by.css('input#field_fechaDeVencimiento'));
    productoSelect = element(by.css('select#field_producto'));
    recepcionDeCompraSelect = element(by.css('select#field_recepcionDeCompra'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCantidadOrdenadaInput = function(cantidadOrdenada) {
        this.cantidadOrdenadaInput.sendKeys(cantidadOrdenada);
    }

    getCantidadOrdenadaInput = function() {
        return this.cantidadOrdenadaInput.getAttribute('value');
    }

    setCantidadRecibidaInput = function(cantidadRecibida) {
        this.cantidadRecibidaInput.sendKeys(cantidadRecibida);
    }

    getCantidadRecibidaInput = function() {
        return this.cantidadRecibidaInput.getAttribute('value');
    }

    setPrecioInput = function(precio) {
        this.precioInput.sendKeys(precio);
    }

    getPrecioInput = function() {
        return this.precioInput.getAttribute('value');
    }

    setNoDeLoteInput = function(noDeLote) {
        this.noDeLoteInput.sendKeys(noDeLote);
    }

    getNoDeLoteInput = function() {
        return this.noDeLoteInput.getAttribute('value');
    }

    setFechaDeVencimientoInput = function(fechaDeVencimiento) {
        this.fechaDeVencimientoInput.sendKeys(fechaDeVencimiento);
    }

    getFechaDeVencimientoInput = function() {
        return this.fechaDeVencimientoInput.getAttribute('value');
    }

    productoSelectLastOption = function() {
        this.productoSelect.all(by.tagName('option')).last().click();
    }

    productoSelectOption = function(option) {
        this.productoSelect.sendKeys(option);
    }

    getProductoSelect = function() {
        return this.productoSelect;
    }

    getProductoSelectedOption = function() {
        return this.productoSelect.element(by.css('option:checked')).getText();
    }

    recepcionDeCompraSelectLastOption = function() {
        this.recepcionDeCompraSelect.all(by.tagName('option')).last().click();
    }

    recepcionDeCompraSelectOption = function(option) {
        this.recepcionDeCompraSelect.sendKeys(option);
    }

    getRecepcionDeCompraSelect = function() {
        return this.recepcionDeCompraSelect;
    }

    getRecepcionDeCompraSelectedOption = function() {
        return this.recepcionDeCompraSelect.element(by.css('option:checked')).getText();
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
