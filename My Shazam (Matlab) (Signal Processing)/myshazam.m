function [ id ] = myshazam( myclip )

table=make_table(myclip);
load HASHTABLE.mat;
rows = size(htable,1)*size(htable,2)/2;
map=NaN(rows,2);
% go through clip and make histogram.
k=1;
for j = 1:size(table,1)
    index = table(j,4)*2^16 +table(j,1)*2^8 +table(j,2);
    index = floor(index);
    for i=1:size(htable,2)/2
        if(htable(index,i)~=0)
            map(k,1) = htable(index,i);
            map(k,2) = htable(index,10+i)-table(j,3);
            k=k+1;
        end
    end
end
map = map(~isnan(map(:,1)),:);
hist(map(:,1));
id=mode(map(:,1));
end

