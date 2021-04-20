import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Amostra = () => import('@/entities/amostra/amostra.vue');
// prettier-ignore
const AmostraUpdate = () => import('@/entities/amostra/amostra-update.vue');
// prettier-ignore
const AmostraDetails = () => import('@/entities/amostra/amostra-details.vue');
// prettier-ignore
const Proprietario = () => import('@/entities/proprietario/proprietario.vue');
// prettier-ignore
const ProprietarioUpdate = () => import('@/entities/proprietario/proprietario-update.vue');
// prettier-ignore
const ProprietarioDetails = () => import('@/entities/proprietario/proprietario-details.vue');
// prettier-ignore
const Propriedade = () => import('@/entities/propriedade/propriedade.vue');
// prettier-ignore
const PropriedadeUpdate = () => import('@/entities/propriedade/propriedade-update.vue');
// prettier-ignore
const PropriedadeDetails = () => import('@/entities/propriedade/propriedade-details.vue');
// prettier-ignore
const Endereco = () => import('@/entities/endereco/endereco.vue');
// prettier-ignore
const EnderecoUpdate = () => import('@/entities/endereco/endereco-update.vue');
// prettier-ignore
const EnderecoDetails = () => import('@/entities/endereco/endereco-details.vue');
// prettier-ignore
const Exame = () => import('@/entities/exame/exame.vue');
// prettier-ignore
const ExameUpdate = () => import('@/entities/exame/exame-update.vue');
// prettier-ignore
const ExameDetails = () => import('@/entities/exame/exame-details.vue');
// prettier-ignore
const Midia = () => import('@/entities/midia/midia.vue');
// prettier-ignore
const MidiaUpdate = () => import('@/entities/midia/midia-update.vue');
// prettier-ignore
const MidiaDetails = () => import('@/entities/midia/midia-details.vue');
// prettier-ignore
const Medicoveterinario = () => import('@/entities/medicoveterinario/medicoveterinario.vue');
// prettier-ignore
const MedicoveterinarioUpdate = () => import('@/entities/medicoveterinario/medicoveterinario-update.vue');
// prettier-ignore
const MedicoveterinarioDetails = () => import('@/entities/medicoveterinario/medicoveterinario-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/amostra',
    name: 'Amostra',
    component: Amostra,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/amostra/new',
    name: 'AmostraCreate',
    component: AmostraUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/amostra/:amostraId/edit',
    name: 'AmostraEdit',
    component: AmostraUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/amostra/:amostraId/view',
    name: 'AmostraView',
    component: AmostraDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/proprietario',
    name: 'Proprietario',
    component: Proprietario,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/proprietario/new',
    name: 'ProprietarioCreate',
    component: ProprietarioUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/proprietario/:proprietarioId/edit',
    name: 'ProprietarioEdit',
    component: ProprietarioUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/proprietario/:proprietarioId/view',
    name: 'ProprietarioView',
    component: ProprietarioDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/propriedade',
    name: 'Propriedade',
    component: Propriedade,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/propriedade/new',
    name: 'PropriedadeCreate',
    component: PropriedadeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/propriedade/:propriedadeId/edit',
    name: 'PropriedadeEdit',
    component: PropriedadeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/propriedade/:propriedadeId/view',
    name: 'PropriedadeView',
    component: PropriedadeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/endereco',
    name: 'Endereco',
    component: Endereco,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/endereco/new',
    name: 'EnderecoCreate',
    component: EnderecoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/endereco/:enderecoId/edit',
    name: 'EnderecoEdit',
    component: EnderecoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/endereco/:enderecoId/view',
    name: 'EnderecoView',
    component: EnderecoDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/exame',
    name: 'Exame',
    component: Exame,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/exame/new',
    name: 'ExameCreate',
    component: ExameUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/exame/:exameId/edit',
    name: 'ExameEdit',
    component: ExameUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/exame/:exameId/view',
    name: 'ExameView',
    component: ExameDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/midia',
    name: 'Midia',
    component: Midia,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/midia/new',
    name: 'MidiaCreate',
    component: MidiaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/midia/:midiaId/edit',
    name: 'MidiaEdit',
    component: MidiaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/midia/:midiaId/view',
    name: 'MidiaView',
    component: MidiaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/medicoveterinario',
    name: 'Medicoveterinario',
    component: Medicoveterinario,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/medicoveterinario/new',
    name: 'MedicoveterinarioCreate',
    component: MedicoveterinarioUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/medicoveterinario/:medicoveterinarioId/edit',
    name: 'MedicoveterinarioEdit',
    component: MedicoveterinarioUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/medicoveterinario/:medicoveterinarioId/view',
    name: 'MedicoveterinarioView',
    component: MedicoveterinarioDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
