import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './vir-server.reducer';
import { IVirServer } from 'app/shared/model/vir-server.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VirServer = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const virServerList = useAppSelector(state => state.virServer.entities);
  const loading = useAppSelector(state => state.virServer.loading);
  const totalItems = useAppSelector(state => state.virServer.totalItems);
  const links = useAppSelector(state => state.virServer.links);
  const entity = useAppSelector(state => state.virServer.entity);
  const updateSuccess = useAppSelector(state => state.virServer.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="vir-server-heading" data-cy="VirServerHeading">
        Vir Servers
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Vir Server
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={virServerList ? virServerList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {virServerList && virServerList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('vpsName')}>
                    Vps Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('remoteHost')}>
                    Remote Host <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('remoteUserName')}>
                    Remote User Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('remotePassword')}>
                    Remote Password <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('remotePort')}>
                    Remote Port <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sessionTimeOut')}>
                    Session Time Out <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('chanelTimeOut')}>
                    Chanel Time Out <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {virServerList.map((virServer, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`${match.url}/${virServer.id}`} color="link" size="sm">
                        {virServer.id}
                      </Button>
                    </td>
                    <td>{virServer.vpsName}</td>
                    <td>{virServer.remoteHost}</td>
                    <td>{virServer.remoteUserName}</td>
                    <td>{virServer.remotePassword}</td>
                    <td>{virServer.remotePort}</td>
                    <td>{virServer.sessionTimeOut}</td>
                    <td>{virServer.chanelTimeOut}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${virServer.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${virServer.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${virServer.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Vir Servers found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default VirServer;