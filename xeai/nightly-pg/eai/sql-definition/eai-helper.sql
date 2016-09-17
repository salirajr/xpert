#direct backup from one to another.
pg_dump -C -h localhost -U postgres xeai | psql -h 192.168.88.136 -U postgres xeai