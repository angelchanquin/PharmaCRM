import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('DetalleDeCompra e2e test', () => {

    let navBarPage: NavBarPage;
    let detalleDeCompraDialogPage: DetalleDeCompraDialogPage;
    let detalleDeCompraComponentsPage: DetalleDeCompraComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load DetalleDeCompras', () => {
        navBarPage.goToEntity('detalle-de-compra');
        detalleDeCompraComponentsPage = new DetalleDeCompraComponentsPage();
        expect(detalleDeCompraComponentsPage.getTitle())
            .toMatch(/pharmacrmApp.detalleDeCompra.home.title/);

    });

    it('should load create DetalleDeCompra dialog', () => {
        detalleDeCompraComponentsPage.clickOnCreateButton();
        detalleDeCompraDialogPage = new DetalleDeCompraDialogPage();
        expect(detalleDeCompraDialogPage.getModalTitle())
            .toMatch(/pharmacrmApp.detalleDeCompra.home.createOrEditLabel/);
        detalleDeCompraDialogPage.close();
    });

   /* it('should create and save DetalleDeCompras', () => {
        detalleDeCompraComponentsPage.clickOnCreateButton();
        detalleDeCompraDialogPage.setCantidadInput('5');
        expect(detalleDeCompraDialogPage.getCantidadInput()).toMatch('5');
        detalleDeCompraDialogPage.setPrecioInput('5');
        expect(detalleDeCompraDialogPage.getPrecioInput()).toMatch('5');
        detalleDeCompraDialogPage.setSubTotalInput('5');
        expect(detalleDeCompraDialogPage.getSubTotalInput()).toMatch('5');
        detalleDeCompraDialogPage.ordenDeCompraSelectLastOption();
        detalleDeCompraDialogPage.productoSelectLastOption();
        detalleDeCompraDialogPage.save();
        expect(detalleDeCompraDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DetalleDeCompraComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-detalle-de-compra div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DetalleDeCompraDialogPage {
    modalTitle = element(by.css('h4#myDetalleDeCompraLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    cantidadInput = element(by.css('input#field_cantidad'));
    precioInput = element(by.css('input#field_precio'));
    subTotalInput = element(by.css('input#field_subTotal'));
    ordenDeCompraSelect = element(by.css('select#field_ordenDeCompra'));
    productoSelect = element(by.css('select#field_producto'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCantidadInput = function(cantidad) {
        this.cantidadInput.sendKeys(cantidad);
    }

    getCantidadInput = function() {
        return this.cantidadInput.getAttribute('value');
    }

    setPrecioInput = function(precio) {
        this.precioInput.sendKeys(precio);
    }

    getPrecioInput = function() {
        return this.precioInput.getAttribute('value');
    }

    setSubTotalInput = function(subTotal) {
        this.subTotalInput.sendKeys(subTotal);
    }

    getSubTotalInput = function() {
        return this.subTotalInput.getAttribute('value');
    }

    ordenDeCompraSelectLastOption = function() {
        this.ordenDeCompraSelect.all(by.tagName('option')).last().click();
    }

    ordenDeCompraSelectOption = function(option) {
        this.ordenDeCompraSelect.sendKeys(option);
    }

    getOrdenDeCompraSelect = function() {
        return this.ordenDeCompraSelect;
    }

    getOrdenDeCompraSelectedOption = function() {
        return this.ordenDeCompraSelect.element(by.css('option:checked')).getText();
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
