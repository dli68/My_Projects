clear;
[y,fs] = audioread('viva.mp3');
y=mean(y,2);
y=y-mean(y);
y=resample(y,8000,fs);
[S,F,T]=spectrogram(y,512,256,512,8000);
S=log(abs(S)+1);
figure(1);
imagesc(T,F,S);
set(gca,'Ydir','Normal')

P=ones(size(S));
threshhold = prctile(reshape(S,1,numel(S)),30*max(T)/(257*344)*100);
threshholdmap = ones(size(S))*threshhold;
for m=1:9
    for n=1:9
        if(m~=5 || n~=5)
            CS=circshift(S,[m-5,n-5]);
            P = P.*((S-CS)>0 .* (S-threshholdmap)>0);
        end
    end
end
figure(2);
imagesc(T,F,P);
set(gca,'Ydir','Normal')
colormap (1-gray);
hold on;

fanout=3;
tem_fanout=0;
table=NaN(numel(T)*numel(F),4);
pt=1;
flag=0;
for m=1:numel(T)
    for n=1:numel(F)
        if(P(n,m)==1)
            for k=5:15
                for q=-10:10
                    if(m+k<=numel(T) && n+q<=numel(F) && n+q>0)
                        if(P(n+q,m+k)==1)
                            table(pt,:)=[F(n) F(n+q) T(m) T(m+k)-T(m)];
                            pt=pt+1;
                            tem_fanout=tem_fanout+1;
                        end
                        if(tem_fanout==fanout)
                            flag=1;
                            break;
                        end
                    end
                end
                if(flag==1)
                    flag=0;
                    tem_fanout=0;
                    break;
                end
            end
        end
    end
end
table = table(~isnan(table(:,1)),:);

for i=1:size(table,1)
    plot([table(i,3),table(i,4)+table(i,3)],[table(i,1),table(i,2)],'-');
    hold on;
end

run('make_database.m');
for i=1:length(listing)
    figure(i+2);
    id = myshazam(listing(i).name);
    fprintf('the name of song is %s; the id of the song is %d\n',...
    listing(id).name,id);
end