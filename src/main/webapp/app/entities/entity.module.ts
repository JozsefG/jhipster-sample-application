import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationOrganizationModule } from './organization/organization.module';
import { JhipsterSampleApplicationTeamModule } from './team/team.module';
import { JhipsterSampleApplicationXGroupModule } from './x-group/x-group.module';
import { JhipsterSampleApplicationXUserModule } from './x-user/x-user.module';
import { JhipsterSampleApplicationRoleModule } from './role/role.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterSampleApplicationOrganizationModule,
        JhipsterSampleApplicationTeamModule,
        JhipsterSampleApplicationXGroupModule,
        JhipsterSampleApplicationXUserModule,
        JhipsterSampleApplicationRoleModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
