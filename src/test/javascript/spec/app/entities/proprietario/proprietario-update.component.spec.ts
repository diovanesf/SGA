/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ProprietarioUpdateComponent from '@/entities/proprietario/proprietario-update.vue';
import ProprietarioClass from '@/entities/proprietario/proprietario-update.component';
import ProprietarioService from '@/entities/proprietario/proprietario.service';

import EnderecoService from '@/entities/endereco/endereco.service';

import PropriedadeService from '@/entities/propriedade/propriedade.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Proprietario Management Update Component', () => {
    let wrapper: Wrapper<ProprietarioClass>;
    let comp: ProprietarioClass;
    let proprietarioServiceStub: SinonStubbedInstance<ProprietarioService>;

    beforeEach(() => {
      proprietarioServiceStub = sinon.createStubInstance<ProprietarioService>(ProprietarioService);

      wrapper = shallowMount<ProprietarioClass>(ProprietarioUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          proprietarioService: () => proprietarioServiceStub,

          enderecoService: () => new EnderecoService(),

          propriedadeService: () => new PropriedadeService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.proprietario = entity;
        proprietarioServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(proprietarioServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.proprietario = entity;
        proprietarioServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(proprietarioServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProprietario = { id: 123 };
        proprietarioServiceStub.find.resolves(foundProprietario);
        proprietarioServiceStub.retrieve.resolves([foundProprietario]);

        // WHEN
        comp.beforeRouteEnter({ params: { proprietarioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.proprietario).toBe(foundProprietario);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
