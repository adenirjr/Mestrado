select documentnumber, name, count(*) from (
select bw.bidid, bw.documentnumber, e.name
from bidding_winner bw 
inner join entity e on (e.documentnumber = bw.documentnumber)
inner join bidding b on (b.bidid = bw.bidid)
where b.total > 50000
group by bw.documentnumber, bw.bidid, e.name) as winners
group by documentnumber, name