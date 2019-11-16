import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAnnualReport, defaultValue } from 'app/shared/model/annual-report.model';

export const ACTION_TYPES = {
  FETCH_ANNUALREPORT_LIST: 'annualReport/FETCH_ANNUALREPORT_LIST',
  FETCH_ANNUALREPORT: 'annualReport/FETCH_ANNUALREPORT',
  CREATE_ANNUALREPORT: 'annualReport/CREATE_ANNUALREPORT',
  UPDATE_ANNUALREPORT: 'annualReport/UPDATE_ANNUALREPORT',
  DELETE_ANNUALREPORT: 'annualReport/DELETE_ANNUALREPORT',
  RESET: 'annualReport/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAnnualReport>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type AnnualReportState = Readonly<typeof initialState>;

// Reducer

export default (state: AnnualReportState = initialState, action): AnnualReportState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ANNUALREPORT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ANNUALREPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ANNUALREPORT):
    case REQUEST(ACTION_TYPES.UPDATE_ANNUALREPORT):
    case REQUEST(ACTION_TYPES.DELETE_ANNUALREPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ANNUALREPORT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ANNUALREPORT):
    case FAILURE(ACTION_TYPES.CREATE_ANNUALREPORT):
    case FAILURE(ACTION_TYPES.UPDATE_ANNUALREPORT):
    case FAILURE(ACTION_TYPES.DELETE_ANNUALREPORT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ANNUALREPORT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_ANNUALREPORT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ANNUALREPORT):
    case SUCCESS(ACTION_TYPES.UPDATE_ANNUALREPORT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ANNUALREPORT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/annual-reports';

// Actions

export const getEntities: ICrudGetAllAction<IAnnualReport> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ANNUALREPORT_LIST,
    payload: axios.get<IAnnualReport>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IAnnualReport> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ANNUALREPORT,
    payload: axios.get<IAnnualReport>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAnnualReport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ANNUALREPORT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAnnualReport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ANNUALREPORT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAnnualReport> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ANNUALREPORT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
