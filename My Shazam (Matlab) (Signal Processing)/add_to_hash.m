function [ htable ] = add_to_hash( songList )

if exist('HASHTABLE.mat','var')
    load HASHTABLE.mat;
else
    maxSongsPerBin = 10;
    maxHashBins = 2^21;
    htable = zeros(maxHashBins,2*maxSongsPerBin);
    numSongs = length(songList);
    songid = NaN(100,1);
end

% add all the songs in the list to the database
for k = 1:numSongs
    table = make_table(songList(k).name);
    for j = 1:size(table,1)
        index = table(j,4)*2^16 +table(j,1)*2^8 +table(j,2);
        index = floor(index);
        for i = 1:maxSongsPerBin
            if(htable(index,i)==0)
                htable(index,i)= k;
                htable(index,i+10)= table(j,3);
                break;
            end
        end
    end
    songid(k)=k;
end
save ('HASHTABLE.mat','htable', 'songid');

end

