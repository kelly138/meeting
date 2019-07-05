import Vue from 'vue'
import Vuex from 'vuex'

import * as actions from './actions'
import * as getters from './getters'
import * as mutations from './mutations'


Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {
    depItems: {},
    meetingItems: {},
    recordsItems: {},
    reviewedItems:{},
    willReviewItems:{},
    roomItems: {},
    // applyRoomItems: {} --------------此思想暂时失败，以后再试
    roomListRecordItems:{},
    depListRecordItems:{},
    DepApplyRecordsItems:{},
    WillReviewItems:{}
  },
  getters,
  mutations,
  actions

})