import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationOrganizationModule } from './organization/organization.module';
import { JhipsterSampleApplicationXTeamModule } from './x-team/x-team.module';
import { JhipsterSampleApplicationXGroupModule } from './x-group/x-group.module';
import { JhipsterSampleApplicationXUserModule } from './x-user/x-user.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterSampleApplicationOrganizationModule,
        JhipsterSampleApplicationXTeamModule,
        JhipsterSampleApplicationXGroupModule,
        JhipsterSampleApplicationXUserModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
