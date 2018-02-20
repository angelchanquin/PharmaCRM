import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Proveedor e2e test', () => {

    let navBarPage: NavBarPage;
    let proveedorDialogPage: ProveedorDialogPage;
    let proveedorComponentsPage: ProveedorComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Proveedors', () => {
        navBarPage.goToEntity('proveedor');
        proveedorComponentsPage = new ProveedorComponentsPage();
        expect(proveedorComponentsPage.getTitle())
            .toMatch(/pharmacrmApp.proveedor.home.title/);

    });

    it('should load create Proveedor dialog', () => {
        proveedorComponentsPage.clickOnCreateButton();
        proveedorDialogPage = new ProveedorDialogPage();
        expect(proveedorDialogPage.getModalTitle())
            .toMatch(/pharmacrmApp.proveedor.home.createOrEditLabel/);
        proveedorDialogPage.close();
    });

    it('should create and save Proveedors', () => {
        proveedorComponentsPage.clickOnCreateButton();
        proveedorDialogPage.setNombreInput('nombre');
        expect(proveedorDialogPage.getNombreInput()).toMatch('nombre');
        proveedorDialogPage.setContactoInput('contacto');
        expect(proveedorDialogPage.getContactoInput()).toMatch('contacto');
        proveedorDialogPage.setCorreoElectronicoInput('correoElectronico');
        expect(proveedorDialogPage.getCorreoElectronicoInput()).toMatch('correoElectronico');
        proveedorDialogPage.setTelefonoInput('telefono');
        expect(proveedorDialogPage.getTelefonoInput()).toMatch('telefono');
        proveedorDialogPage.setCelularInput('celular');
        expect(proveedorDialogPage.getCelularInput()).toMatch('celular');
        proveedorDialogPage.setSitioWebInput('sitioWeb');
        expect(proveedorDialogPage.getSitioWebInput()).toMatch('sitioWeb');
        proveedorDialogPage.setDireccionInput('direccion');
        expect(proveedorDialogPage.getDireccionInput()).toMatch('direccion');
        proveedorDialogPage.save();
        expect(proveedorDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ProveedorComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-proveedor div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ProveedorDialogPage {
    modalTitle = element(by.css('h4#myProveedorLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nombreInput = element(by.css('input#field_nombre'));
    contactoInput = element(by.css('input#field_contacto'));
    correoElectronicoInput = element(by.css('input#field_correoElectronico'));
    telefonoInput = element(by.css('input#field_telefono'));
    celularInput = element(by.css('input#field_celular'));
    sitioWebInput = element(by.css('input#field_sitioWeb'));
    direccionInput = element(by.css('input#field_direccion'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNombreInput = function(nombre) {
        this.nombreInput.sendKeys(nombre);
    }

    getNombreInput = function() {
        return this.nombreInput.getAttribute('value');
    }

    setContactoInput = function(contacto) {
        this.contactoInput.sendKeys(contacto);
    }

    getContactoInput = function() {
        return this.contactoInput.getAttribute('value');
    }

    setCorreoElectronicoInput = function(correoElectronico) {
        this.correoElectronicoInput.sendKeys(correoElectronico);
    }

    getCorreoElectronicoInput = function() {
        return this.correoElectronicoInput.getAttribute('value');
    }

    setTelefonoInput = function(telefono) {
        this.telefonoInput.sendKeys(telefono);
    }

    getTelefonoInput = function() {
        return this.telefonoInput.getAttribute('value');
    }

    setCelularInput = function(celular) {
        this.celularInput.sendKeys(celular);
    }

    getCelularInput = function() {
        return this.celularInput.getAttribute('value');
    }

    setSitioWebInput = function(sitioWeb) {
        this.sitioWebInput.sendKeys(sitioWeb);
    }

    getSitioWebInput = function() {
        return this.sitioWebInput.getAttribute('value');
    }

    setDireccionInput = function(direccion) {
        this.direccionInput.sendKeys(direccion);
    }

    getDireccionInput = function() {
        return this.direccionInput.getAttribute('value');
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
